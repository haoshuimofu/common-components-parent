#总结
##运单查询
###查询特点
1.查询量大，并发高；  
2.单条数据更新频次高；  
3.数据信息大，垂直分了十几个Content；

###查询方案
1.使用Redis作为DB上一层的cache;  
2.查询Cache，如果命中则返回，miss则查询DB，查询时不写cache;  
3.运单DB更新的时候更新Cache，读和写完全分离；  
4.为了避免大key和热点数据，key=prefix:id:content
