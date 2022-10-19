package com.gl.springbootservice.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gl.springbootcommon.constants.Constant;
import com.gl.springbootservice.dto.input.BooksInputDTO;
import com.gl.springbootservice.dto.input.RemoveBatchInputDTO;
import com.gl.springbootservice.dto.output.BooksOutputDTO;
import com.gl.springbootservice.dto.output.PageBookOutputDTO;
import com.gl.springbootcommon.annotation.ApiChecked;
import com.gl.springbootcommon.constants.ErrorEnum;
import com.gl.springbootcommon.constants.StatusEnum;
import com.gl.springbootcommon.model.PageBean;
import com.gl.springbootcommon.model.R;
import com.gl.springbootcommon.utils.BeanUtilCopy;
import com.gl.springbootdao.entity.BookDO;
import com.gl.springbootdao.mapper.BookMapper;
import com.gl.springbootservice.BookService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import cn.hutool.core.bean.BeanUtil;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Future;

/**
 * <p>
 * 书籍表 服务实现类
 * </p>
 *
 * @author gelei
 * @since 2022-09-23
 */
@Service
public class BookServiceImpl extends ServiceImpl<BookMapper, BookDO> implements BookService {
    Logger logger = LoggerFactory.getLogger(BookService.class);
    @Autowired
    private Constant constant;

    @Override
    @ApiChecked(value = "根据id获取书籍")
    public R getBookById(Integer id) {
        LambdaQueryWrapper<BookDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(BookDO::getId, id);
        queryWrapper.eq(BookDO::getStatus, StatusEnum.ENABLE.getCode());
        return R.ok().data(this.list(queryWrapper));
    }

    @Override
    @ApiChecked(value = "获取书籍列表")
    public R getList() {
        LambdaQueryWrapper<BookDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(BookDO::getStatus, StatusEnum.ENABLE.getCode());
        return R.ok().data(this.list(queryWrapper));
    }

    @Override
    @ApiChecked(value = "新增书籍")
    public R saveBook(BooksInputDTO booksInputDTO) {
        if (StringUtils.isBlank(booksInputDTO.getName()) || StringUtils.isBlank(booksInputDTO.getType())) {
            return R.error(ErrorEnum.BAD_REQUEST.getCode(), ErrorEnum.BAD_REQUEST.getDesc());
        }
        if (booksInputDTO.getId() != null) {
            return R.error(ErrorEnum.BAD_REQUEST.getCode(), ErrorEnum.BAD_REQUEST.getDesc());
        }
        BookDO bookDO = BeanUtil.copyProperties(booksInputDTO, BookDO.class);
        if (this.save(bookDO)) {
            return R.ok();
        }
        return R.error(ErrorEnum.OPERATE_ERROR.getCode(), ErrorEnum.OPERATE_ERROR.getDesc());
    }

    @Override
    @ApiChecked(value = "修改书籍")
    public R updateBook(BooksInputDTO booksInputDTO) {
        if (booksInputDTO.getId() == null) {
            return R.error(ErrorEnum.BAD_REQUEST.getCode(), ErrorEnum.BAD_REQUEST.getDesc());
        }
        BookDO bookDO = BeanUtil.copyProperties(booksInputDTO, BookDO.class);
        if (this.updateById(bookDO)) {
            return R.ok();
        }
        return R.error(ErrorEnum.OPERATE_ERROR.getCode(), ErrorEnum.OPERATE_ERROR.getDesc());
    }

    @Override
    @ApiChecked(value = "批量删除书籍")
    public R removeBatch(RemoveBatchInputDTO removeBatchInputDTO) {
        if (CollectionUtils.isEmpty(removeBatchInputDTO.getIdList())) {
            return R.error(ErrorEnum.BAD_REQUEST.getCode(), ErrorEnum.BAD_REQUEST.getDesc());
        }
        LambdaUpdateWrapper<BookDO> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(BookDO::getStatus, StatusEnum.DISABLE.getCode());
        updateWrapper.in(BookDO::getId, removeBatchInputDTO.getIdList());
        this.update(updateWrapper);
        return R.ok();
    }

    @Override
    @Async("threadPoolTaskExecutor")
    @ApiChecked(value = "分页获取书籍列表")
    public Future<PageBookOutputDTO> getPage(PageBean pageBean) {
        boolean flag = Arrays.asList(constant.getName().split(",")).contains("S6210A_Sampler");
        logger.info("是否新设备："+ flag);
        LambdaQueryWrapper<BookDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(BookDO::getStatus, StatusEnum.ENABLE.getCode());
        Page<BookDO> bookDOPage = this.page(pageBean.makePaging(), queryWrapper);
        return new AsyncResult<>(DO2Model(bookDOPage));
    }

    @Override
    @Async("threadPoolTaskExecutor")
    @ApiChecked(value = "模拟异步方法1")
    public Future<List<BooksOutputDTO>> getData1() throws InterruptedException {
        LambdaQueryWrapper<BookDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(BookDO::getStatus, StatusEnum.ENABLE.getCode());
        List<BookDO> bookDOS = this.list(queryWrapper);
        List<BooksOutputDTO> booksOutputDTOList = BeanUtilCopy.copyListProperties(bookDOS, BooksOutputDTO::new);
        Thread.sleep(1000);
        return new AsyncResult<>(booksOutputDTOList);
    }

    @Override
    @Async("threadPoolTaskExecutor")
    @ApiChecked(value = "模拟异步方法2")
    public Future<List<BooksOutputDTO>> getData2() throws InterruptedException {
        LambdaQueryWrapper<BookDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(BookDO::getStatus, StatusEnum.ENABLE.getCode());
        List<BookDO> bookDOS = this.list(queryWrapper);
        List<BooksOutputDTO> booksOutputDTOList = BeanUtilCopy.copyListProperties(bookDOS, BooksOutputDTO::new);
        Thread.sleep(3000);
        return new AsyncResult<>(booksOutputDTOList);
    }

    /**
     * 数据转换
     * @param page
     * @return
     */
    private PageBookOutputDTO DO2Model(Page<BookDO> page) {
        List<BooksOutputDTO> booksOutputDTOList = BeanUtilCopy.copyListProperties(page.getRecords(), BooksOutputDTO::new);
        PageBookOutputDTO pageBookOutputDTO = new PageBookOutputDTO();
        pageBookOutputDTO.setPages(page.getPages());
        pageBookOutputDTO.setCurrentPage(page.getCurrent());
        pageBookOutputDTO.setSize(page.getSize());
        pageBookOutputDTO.setTotal(page.getTotal());
        pageBookOutputDTO.setRecords(booksOutputDTOList);
        return pageBookOutputDTO;
    }
}
