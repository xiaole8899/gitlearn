package org.dppc.mtrace.frame.base;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Page<T extends Serializable> implements Serializable {
	private static final long serialVersionUID = -2058292291727710603L;
	// 默认页面容量 是 20 条数据
	private static final int DEFAULT_PAGE_SIZE = 20;
	private static final int DEFAULT_PAGE_INDEX =1;
	// 当前页号
	private int pageIndex;
	// 页面大小（每页数据行数）
	private int pageSize;

	// 本页数据行
	private List<T> rows = new ArrayList<T>();
	// 本页数据行大小
	private int rowSize;

	// 总页数
	private int totalPages;
	// 数据行数
	private int totalRows;
	
	
	public Page() {
		pageSize = DEFAULT_PAGE_SIZE;
	}

	public Page(int pageIndex) {
		this();
		this.pageIndex = pageIndex;
	}
	
	public Page(int pageIndex, int pageSize) {
		this.pageIndex =pageIndex > 0 ? pageIndex : DEFAULT_PAGE_INDEX;
		this.pageSize =pageSize > 0 ? pageSize : DEFAULT_PAGE_SIZE;
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public List<T> getRows() {
		return rows;
	}

	public void setRows(List<T> rows) {
		this.rows = rows;
		this.rowSize =rows.size();
	}

	public int getRowSize() {
		return rowSize;
	}

	public void setRowSize(int rowSize) {
		this.rowSize = rowSize;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public int getTotalRows() {
		return totalRows;
	}

	public void setTotalRows(int totalRows) {
		this.totalRows = totalRows;
		this.totalPages =(totalRows +pageSize -1) /pageSize;
	}

}
