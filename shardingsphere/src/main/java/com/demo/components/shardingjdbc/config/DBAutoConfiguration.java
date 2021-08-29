package com.demo.components.shardingjdbc.config;

import com.demo.components.shardingjdbc.utils.DataSourceFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.api.config.sharding.ShardingRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.TableRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.strategy.ComplexShardingStrategyConfiguration;
import org.apache.shardingsphere.api.config.sharding.strategy.InlineShardingStrategyConfiguration;
import org.apache.shardingsphere.api.config.sharding.strategy.ShardingStrategyConfiguration;
import org.apache.shardingsphere.api.config.sharding.strategy.StandardShardingStrategyConfiguration;
import org.apache.shardingsphere.api.sharding.complex.ComplexKeysShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.complex.ComplexKeysShardingValue;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;
import org.apache.shardingsphere.shardingjdbc.api.ShardingDataSourceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionManager;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableConfigurationProperties(value = {DatabaseConfig.class})
//@Import(DatabaseConfig.class)
@Slf4j
public class DBAutoConfiguration {

    @Autowired
    private DatabaseConfig databaseConfig;

    @Bean
    DataSource getShardingDataSource() throws SQLException {
        ShardingRuleConfiguration shardingRuleConfig = new ShardingRuleConfiguration();
        shardingRuleConfig.getTableRuleConfigs().add(getOrderTableRuleConfiguration());
//        shardingRuleConfig.getBindingTableGroups().add("t_order");
//        shardingRuleConfig.getBroadcastTables().add("t_order");// 不能广播
//        shardingRuleConfig.setDefaultDatabaseShardingStrategyConfig(new InlineShardingStrategyConfiguration("oder_id", "ds${order_id % 2}"));
//        shardingRuleConfig.setDefaultTableShardingStrategyConfig(new StandardShardingStrategyConfiguration("order_id", new ModuloShardingTableAlgorithm()));
        return ShardingDataSourceFactory.createDataSource(createDataSourceMap(), shardingRuleConfig, null);
    }

    @Bean
    TableRuleConfiguration getOrderTableRuleConfiguration() {
        TableRuleConfiguration tableRuleConfiguration = new TableRuleConfiguration("t_order", "ds_${0..1}.t_order_${[0, 1]}");
        tableRuleConfiguration = new TableRuleConfiguration("t_order","ds_${0..1}.t_order_${0..1}");
        // 数据库的分配策略, ds_(order_id)%2
        ShardingStrategyConfiguration databaseConfig = new StandardShardingStrategyConfiguration("order_id", new PreciseShardingAlgorithm<String>() {
            @Override
            public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<String> shardingValue) {
                String dsName = "ds_" + Integer.parseInt(shardingValue.getValue()) % 2;
                log.error("### {}({}={})路由到到database=[{}]",
                        shardingValue.getLogicTableName(),
                        shardingValue.getColumnName(),
                        shardingValue.getValue(), dsName);
                return availableTargetNames.stream().filter(e -> e.equals(dsName)).findFirst().get();
            }
        });
        tableRuleConfiguration.setDatabaseShardingStrategyConfig(databaseConfig);

        // 表的分配策略
        ShardingStrategyConfiguration tableConfig = new StandardShardingStrategyConfiguration("order_id", new PreciseShardingAlgorithm<String>() {
            @Override
            public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<String> shardingValue) {
                String tbName = "t_order_" + Integer.parseInt(shardingValue.getValue()) % 2;
                log.error("### {}({}={})路由到到table=[{}]",
                        shardingValue.getLogicTableName(),
                        shardingValue.getColumnName(),
                        shardingValue.getValue(), tbName);
                return availableTargetNames.stream().filter(e -> e.equals(tbName)).findFirst().get();
            }
        });
        tableRuleConfiguration.setTableShardingStrategyConfig(tableConfig);
//        tableRuleConfiguration.setKeyGeneratorConfig(null);
        return tableRuleConfiguration;
    }


    @Bean
    Map<String, DataSource> createDataSourceMap() {
        Map<String, DataSource> result = new HashMap<>();
        result.put("ds_0", DataSourceFactory.dataSource(databaseConfig.getConfig().get("ds_0")));
        result.put("ds_1", DataSourceFactory.dataSource(databaseConfig.getConfig().get("ds_1")));
        return result;
    }
}
