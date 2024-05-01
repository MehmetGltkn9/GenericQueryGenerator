package Java;

import jakarta.jms.Connection;
//import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
//import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GenericDAO<T> {  
    
    private final Connection connection;

    public GenericDAO(Connection connection) {
        this.connection = connection;
    }

//    public void insert(T entity) throws SQLException, IntrospectionException{
//        insert(entity, getClassName((Class<?>) entity)) ;
//    }

    public void insertEntity(T entity) throws SQLException, IllegalAccessException, IntrospectionException{
        insertEntity(entity, getClassName((Class<?>) entity)) ;
    }
    
//    public void insert(T entity, String tableName) throws SQLException, IntrospectionException {
//        Field[] fields = entity.getClass().getDeclaredFields();
////        for (int i = 0; i < fields.length; i++) {
////            Field field = fields[i];
////            field.setAccessible(true); 
////
////            sqlBuilder.append(field.getName());
////            if (i < fields.length - 1) {
////                sqlBuilder.append(", ");
////            }
////        }
////    List<String> properties = getAllPropertiesWithDescriptor(entity.getClass());  // veya  getAllPropertiesWithFields(entity.getClass()); 
//    StringBuilder sqlBuilder = new StringBuilder();
//    sqlBuilder.append("INSERT INTO ").append(tableName).append(" (");
//
//    for (int i = 0; i < properties.size(); i++) {
//        sqlBuilder.append(properties.get(i));
//        if (i < properties.size() - 1) {
//            sqlBuilder.append(", ");
//        }
//    }
//        System.out.println(properties);
//    sqlBuilder.append(") VALUES (");
//
//    List<String> propertiesValues = getAllPropertiesValues(entity, properties);
//    if(propertiesValues != null){
//        
//        if(!propertiesValues.isEmpty()){
//            for (int i = 1; i < propertiesValues.size(); i++) {
//                sqlBuilder.append(propertiesValues.get(i));
//                if (i < properties.size() - 1) {
//                        sqlBuilder.append(", ");
//                }
//            }
//        }
//        else{
//            for (int i = 0; i < properties.size(); i++) {
//                sqlBuilder.append("NULL");
//                if (i < properties.size() - 1) {
//                    sqlBuilder.append(", ");
//                }
//            }
//        }
//    }
//    else{
//        for (int i = 0; i < properties.size(); i++) {
//            sqlBuilder.append("NULL");
//            if (i < properties.size() - 1) {
//                sqlBuilder.append(", ");
//            }
//        }
//    }
//    
//
//    sqlBuilder.append(")");
//
//    String sql = sqlBuilder.toString();
//    
//        //System.out.println(sql);
////    try (PreparedStatement statement = connection.prepareStatement(sql)) {
////        // Değerleri PreparedStatement ile set et
////        for (int i = 0; i < properties.size(); i++) {
////            PropertyDescriptor propertyDescriptor = new PropertyDescriptor(properties.get(i), entity.getClass());
////            Object value = propertyDescriptor.getReadMethod().invoke(entity);
////            statement.setObject(i + 1, value);
////        }
////
////        statement.executeUpdate();
////    } catch (Exception e) {
////        e.printStackTrace();
////        // Gerekli hata işlemleri
////    }
//}
    
    public void insertEntity(T entity, String tableName) throws SQLException, IllegalAccessException {
        Class<?> classs = entity.getClass();
        Field[] fields = classs.getDeclaredFields();

        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("INSERT INTO ").append(tableName).append(" (");

        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            field.setAccessible(true); 

            sqlBuilder.append(field.getName());
            if (i < fields.length - 1) {
                sqlBuilder.append(", ");
            }
        }

        sqlBuilder.append(") VALUES (");

        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            field.setAccessible(true);

            Object value = field.get(entity);
            if (value instanceof String) {
                sqlBuilder.append("'").append(value).append("'");
            } 
            else if(value == null){
                sqlBuilder.append("NULL");
            }
            else {
                sqlBuilder.append(value);
            }

            if (i < fields.length - 1) {
                sqlBuilder.append(", ");
            }
        }

        sqlBuilder.append(")");

        String sql = sqlBuilder.toString();
        
        System.out.println(sql);
//        try (PreparedStatement statement = connection.prepareStatement(sql)) {
//            statement.executeUpdate();
//        }
    }
    
    public String getClassName(Class<?> entity) {
        return entity.getSimpleName();
    }
  
    public List<String> getAllFields(Class<?> entity) {
        List<String> properties = new ArrayList<>();
        Field[] fields = entity.getDeclaredFields();

        for (Field field : fields) {
            properties.add(field.getName());
        }

        return properties;
    }
    
//   public List<String> getAllPropertiesWithDescriptor(Class<?> entity) throws IntrospectionException {
//    
//        java.beans.PropertyDescriptor[] descriptors = java.beans.Introspector.getBeanInfo(Car.class).getPropertyDescriptors();
//        java.util.List<String> fieldNames = new java.util.ArrayList<>();
//        for (java.beans.PropertyDescriptor descriptor : descriptors) {
//            if (!descriptor.getName().equals("class")) { // Exclude the "class" property
//                fieldNames.add(descriptor.getName());
//            }
//        }
//
//        return fieldNames;
//    }

    
    public List<String> getAllPropertiesValues(T entity, List<String> fieldNames ) throws IntrospectionException{
        List<String> PropertiesValues  = new ArrayList<>();
        for (String fieldName : fieldNames) {
            String getterMethodName = "get" + capitalize(fieldName); 
            try {
                Method getterMethod = entity.getClass().getMethod(getterMethodName); 
                Object value = getterMethod.invoke(entity);            
                PropertiesValues.add(""+value);
                
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                System.out.println("Java.GenericDAO.getAllPropertiesValues()");
            }
        }
        
       //System.out.println(PropertiesValues);
//        BeanInfo beanInfo = Introspector.getBeanInfo(entity.getClass());
//        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
//        
//        Map<String, Object> propertiesMap = new HashMap<>();   
//        
//        Object value;
//        for (int i = 0; i < propertiesMap.size(); i++) {
//            value = propertiesMap.get(propertyDescriptors[i].getName());
//            PropertiesValues.add((String)value);
//        }
        
        return PropertiesValues;
    }

    public static String capitalize(String str) {
        if(str.startsWith("i")){
            str = "I" + str.substring(1);
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    public void getEntity(T entity, String tableName) throws IllegalAccessException {
    Class<?> classs = entity.getClass();
    Field[] fields = classs.getDeclaredFields();

    StringBuilder sqlBuilder = new StringBuilder();
    sqlBuilder.append("SELECT ");

    for (int i = 0; i < fields.length; i++) {
        Field field = fields[i];
        field.setAccessible(true); 

        if (i > 0) {
            sqlBuilder.append(", ");
        }

        sqlBuilder.append(field.getName());
    }

    sqlBuilder.append(" FROM ").append(tableName);
    String sqlQuery = sqlBuilder.toString();

    System.out.println(sqlQuery); 

}
    public void deleteEntity(T entity, String tableName) throws IllegalAccessException {
    Class<?> classs = entity.getClass();
    List<Field> fields = new ArrayList<>(Arrays.asList(classs.getDeclaredFields()));
    
    
    StringBuilder deleteSqlBuilder = new StringBuilder();
    deleteSqlBuilder.append("DELETE FROM ").append(tableName);
    deleteSqlBuilder.append(" WHERE ");

    for (int i = 0; i < fields.size(); i++) {
            Field field = fields.get(i);
            field.setAccessible(true);

            Object value = field.get(entity);
            if (value instanceof String) {
                deleteSqlBuilder.append(field.getName()).append(" = '").append(value).append("'");
            } 
            else if(value == null){
                
            }
            else {
                deleteSqlBuilder.append(field.getName()).append(" = '").append(value).append("'");
            }
            if (i < fields.size() - 1) {
                deleteSqlBuilder.append(", ");
            }
            
        }
 

    String deleteSqlQuery = deleteSqlBuilder.toString();

    System.out.println(deleteSqlQuery);
}

    public void updateEntity(T entity, String tableName, int id) throws IllegalAccessException {
    Class<?> classs = entity.getClass();
    Field[] fields = classs.getDeclaredFields();

    StringBuilder updateSqlBuilder = new StringBuilder();
    updateSqlBuilder.append("UPDATE ").append(tableName).append(" SET ");

    for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            field.setAccessible(true);

            Object value = field.get(entity);
            if (value instanceof String) {
                updateSqlBuilder.append(field.getName()).append(" = '").append(value).append("'");
            } 
            else if(value == null){
                
            }
            else {
                updateSqlBuilder.append(field.getName()).append(" = '").append(value).append("'");
            }
            if (i < fields.length - 1) {
                updateSqlBuilder.append(", ");
            }
            
        }

    updateSqlBuilder.append(" WHERE ");

//    for (int i = 0; i < fields.length; i++) {
//        Field field = fields[i];
//        field.setAccessible(true); 
//
//        if (i > 0) {
//            updateSqlBuilder.append(" AND ");
//        }
//
//        updateSqlBuilder.append(field.getName()).append("=?");
//    }
    
    updateSqlBuilder.append("id = '"+id+"'");

    String updateSqlQuery = updateSqlBuilder.toString();

    System.out.println(updateSqlQuery);

}


}