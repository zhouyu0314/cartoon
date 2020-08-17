package com.cartoon.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 此为新版的分页工具，老版的不适用于微服，微服之间通过http发请求会将
 * provider的PageUtil<T>类型数据先通过get/set方法转换成json格式
 * 然后consumer接收后会再通过PageUtil<T>的get/set方法将json数据
 * 转换成PageUtil<T>，由于老版本某些字段缺少get/set或者get/set里面夹杂了其他的逻辑
 * 故转换不成功，会报byZeor错误。
 * 此版本使用方法和老版本的基本一致，例：
 * //创建分页对象
 * PageUtil<User> pageUtil = new PageUtil();
 * //设置总条数
 * pageUtil.setTotalCount(int totalCount);
 * //设置页面显示数量
 * pageUtil.sPageSize(int pageSize);
 * //设置当前页面数
 * pageUtil.sCurrentPage(int currentPage);
 * //获取起始页数
 * pageUtil.gStartRow()
 *
 * @param <T>
 */

public class PageUtil<T> {
    //当前页
    private int currentPage;
    //页面容量（条数）
    private int pageSize;
    //起始条数
    private int startRow;
    //总条数
    private int totalCount;
    //总页数
    private int totalPage;
    private List<T> lists = new ArrayList<T>();
    private Set<T> sets = new HashSet<>();

    public Set<T> getSets() {
        return sets;
    }

    public void setSets(Set<T> sets) {
        this.sets = sets;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getStartRow() {
        return startRow;
    }

    public void setStartRow(int startRow) {
        this.startRow = startRow;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public List<T> getLists() {
        return lists;
    }

    public void setLists(List<T> lists) {
        this.lists = lists;
    }


    public void sCurrentPage(int currentPage) {
        if (currentPage <= 0) {
            currentPage = 1;
        } else if (currentPage > this.gTotalPage()) {
            if (this.gTotalPage() == 0) {
                currentPage = 1;
            } else {
                currentPage = this.gTotalPage();
            }
        }
        this.currentPage = currentPage;
    }


    public void sPageSize(int pageSize) {
        if (pageSize <= 0) {
            pageSize = 5;
        }
        this.pageSize = pageSize;
    }

    public int gStartRow() {
        return (this.getCurrentPage() - 1) * this.getPageSize();
    }

    public int gTotalPage() {
        if (this.getTotalCount() % this.getPageSize() == 0) {
            this.totalPage = this.getTotalCount() / this.getPageSize();
        } else {
            this.totalPage = this.getTotalCount() / this.getPageSize() + 1;
        }
        return this.totalPage;
    }


}
