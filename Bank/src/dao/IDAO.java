package dao;

import java.util.ArrayList;

public interface IDAO<Type> {
    public void insert(Type type);

    public void update(Type type);

    public void delete(String id);

    public ArrayList<Type> selectAll();

    public Type selectById(String id);

    public ArrayList<Type> selectByCondition(String condition);
}
