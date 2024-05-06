package net.lanet.tabelafipe.service;

import java.util.List;

public interface IConvertsData {
    <T> T getData(String json, Class<T> classModel);
    <T> List<T> getList(String json, Class<T> classModel);
}
