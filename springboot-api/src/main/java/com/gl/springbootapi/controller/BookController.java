package com.gl.springbootapi.controller;


import com.alibaba.fastjson.JSON;
import com.gl.springbootasync.utils.JobSchedule;
import com.gl.springbootcommon.constants.ErrorEnum;
import com.gl.springbootcommon.model.PageBean;
import com.gl.springbootcommon.model.R;
import com.gl.springbootservice.dto.input.BooksInputDTO;
import com.gl.springbootservice.dto.input.RemoveBatchInputDTO;
import com.gl.springbootservice.dto.output.BooksOutputDTO;
import com.gl.springbootservice.BookService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@RestController
@Slf4j
@RequestMapping("/books")
public class BookController {
    @Autowired
    private BookService iBookService;

    @GetMapping("/get")
    public R getAll() {
        return iBookService.getList();
    }

    @PostMapping("/save")
    public R save(@RequestBody BooksInputDTO book) {
        return iBookService.saveBook(book);
    }

    @PostMapping("/update")
    public R update(@RequestBody BooksInputDTO book) {
        return iBookService.updateBook(book);
    }

    @PostMapping("/removeBatch")
    public R removeBatch(@RequestBody RemoveBatchInputDTO removeBatchInputDTO) {
        return iBookService.removeBatch(removeBatchInputDTO);
    }

    @GetMapping("/getById")
    public R getById(Integer id) {
        if (id == null) return R.error(ErrorEnum.BAD_REQUEST.getCode(), ErrorEnum.BAD_REQUEST.getDesc());
        return iBookService.getBookById(id);
    }

    @PostMapping("/page")
    public R getPage(@RequestBody PageBean pageBean) throws ExecutionException, InterruptedException {
        return R.ok().data(iBookService.getPage(pageBean).get());
    }

    @GetMapping("/testThread")
    public R testThread() throws InterruptedException, ExecutionException {
        Future<List<BooksOutputDTO>> f1 = iBookService.getData1();
        Future<List<BooksOutputDTO>> f2 = iBookService.getData2();
        List<BooksOutputDTO> list1 = f1.get();
        List<BooksOutputDTO> list2 = f2.get();
        System.out.println(JSON.toJSONString(list1));
        System.out.println(JSON.toJSONString(list2));
        return R.ok();
    }

    @GetMapping("/updateCron")
    public String updateCron(String cron) throws SchedulerException {
        log.info("new cron :{}", cron);
        String job_name = "动态任务调度";
        JobSchedule.modifyJobTime(job_name, cron);
//        JobSchedule.addJob(job_name, HelloJob.class, "*/10 * * * * ?");
        return "ok";
    }
}