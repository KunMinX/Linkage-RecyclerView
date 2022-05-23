package com.kunminx.linkagelistview.bean;

/**
 * 提供一个自定义的后台数据实体类，作为示例
 * <p>
 * Create by KunMinX at 2022/5/23
 */
public class CustomSampleBean {

  public String sort;
  public String type;
  public String name;
  public String group;
  public String date;

  public CustomSampleBean(String sort, String type, String name, String group, String date) {
    this.sort = sort;
    this.type = type;
    this.name = name;
    this.group = group;
    this.date = date;
  }

  public String getSort() {
    return sort;
  }

  public void setSort(String sort) {
    this.sort = sort;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getGroup() {
    return group;
  }

  public void setGroup(String group) {
    this.group = group;
  }

  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }
}
