package com.gl.springbootservice;


import com.baomidou.mybatisplus.extension.service.IService;
import com.gl.springbootservice.dto.input.BooksInputDTO;
import com.gl.springbootservice.dto.input.RemoveBatchInputDTO;
import com.gl.springbootservice.dto.output.BooksOutputDTO;
import com.gl.springbootservice.dto.output.PageBookOutputDTO;
import com.gl.springbootcommon.model.PageBean;
import com.gl.springbootcommon.model.R;
import com.gl.springbootdao.entity.BookDO;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * <p>
 * 书籍表 服务类
 * </p>
 *
 * @author gelei
 * @since 2022-09-23
 */
public interface BookService extends IService<BookDO> {

    R getBookById(Integer id);

    R getList();

    R saveBook(BooksInputDTO booksInputDTO);

    R updateBook(BooksInputDTO booksInputDTO);

    R removeBatch(RemoveBatchInputDTO removeBatchInputDTO);

    Future<PageBookOutputDTO> getPage(PageBean pageBean) throws ExecutionException, InterruptedException;

    Future<List<BooksOutputDTO>> getData1() throws ExecutionException, InterruptedException;

    Future<List<BooksOutputDTO>> getData2() throws ExecutionException, InterruptedException;
}
