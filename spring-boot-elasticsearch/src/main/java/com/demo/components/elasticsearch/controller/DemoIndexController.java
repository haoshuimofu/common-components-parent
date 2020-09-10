package com.demo.components.elasticsearch.controller;

import com.demo.components.elasticsearch.JsonResult;
import com.demo.components.elasticsearch.model.Demo;
import com.demo.components.elasticsearch.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * @author wude
 * @date 2020/4/18 17:12
 */
@RestController
@RequestMapping("/demo/index")
public class DemoIndexController {

    @Autowired
    private DemoService demoService;

    @PostMapping("save")
    public JsonResult saveGoodsIndex(@RequestBody Demo demo,
                                     @RequestParam(value = "async", defaultValue = "false") boolean async) {
        return JsonResult.success(demoService.saveDemoIndex(demo, async));
    }

    @PostMapping("bulkSave")
    public JsonResult bulkSaveGoodsIndex(@RequestBody List<Demo> demoList,
                                         @RequestParam(value = "async", defaultValue = "false") boolean async) {
        return JsonResult.success(demoService.bulkSaveDemoIndex(demoList, async));
    }

    @PostMapping("update")
    public JsonResult updateGoodsIndex(@RequestBody Demo demo,
                                       @RequestParam(value = "async", defaultValue = "false") boolean async) {
        return JsonResult.success(demoService.updateDemoIndex(demo, async));
    }

    @PostMapping("bulkUpdate")
    public JsonResult bulkUpdateGoodsIndex(@RequestBody List<Demo> demoList,
                                           @RequestParam(value = "async", defaultValue = "false") boolean async) {
        return JsonResult.success(demoService.bulkUpdateDemoIndex(demoList, async));
    }

    @GetMapping("getById")
    public JsonResult getById(@RequestParam("id") String id,
                              @RequestParam(value = "routing", required = false) String routing) throws Exception {
        return JsonResult.success(demoService.getById(id, routing));
    }

    @GetMapping("getByIds")
    public JsonResult getByIds(@RequestParam("ids") String ids,
                               @RequestParam(value = "routing", required = false) String routing) throws Exception {
        return JsonResult.success(demoService.getByIds(Arrays.asList(ids.split(",")), routing));
    }

    @GetMapping("deleteById")
    public JsonResult deleteById(@RequestParam("id") String id,
                                 @RequestParam(value = "routing", defaultValue = "") String routing) {
        return JsonResult.success(demoService.deleteById(id, routing));
    }

    @GetMapping("deleteByIds")
    public JsonResult deleteByIds(@RequestParam String ids,
                                  @RequestParam(value = "routing", defaultValue = "") String routing) {
        return JsonResult.success(demoService.deleteByIds(Arrays.asList(ids.split(",")), routing));
    }

    @GetMapping("searchByKeyword")
    public JsonResult searchByKeyword(@RequestParam(value = "keyword", defaultValue = "") String keyword,
                                      @RequestParam(value = "from", defaultValue = "0") int from,
                                      @RequestParam(value = "size", defaultValue = "10") int size) {
        return JsonResult.success(demoService.searchByKeyword(keyword, from, size));
    }

    @GetMapping("page")
    public JsonResult page(@RequestParam(value = "from", defaultValue = "0") int from,
                           @RequestParam(value = "size", defaultValue = "10") int size) throws Exception {
        return JsonResult.success(demoService.page(from, size));
    }

}