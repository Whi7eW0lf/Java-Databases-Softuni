package orm;

import annotation.Entity;
import annotation.Id;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class EntityManager<E> implements DbContext<E> {

    private Connection connection;

    public EntityManager(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean persist(E entity) {

        Field primary = getId(entity.getClass());

        primary.setAccessible(true);

        String tableName = getTableName(entity.getClass());

        String query = generateInsertQuery(entity,tableName);


        try {
            return this.connection.prepareStatement(query).execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public Iterable<E> find(Class<E> table) {
        return null;
    }

    @Override
    public Iterable<E> find(Class<E> table, String where) {
        return null;
    }

    @Override
    public E findFirst(Class<E> table) {
        return null;
    }

    @Override
    public E findFirst(Class<E> table, String where) {
        return null;
    }

    private Field getId(Class entity) {
        return Arrays.stream(entity.getDeclaredFields())
                .filter(e -> e.isAnnotationPresent(Id.class))
                .findFirst()
                .orElseThrow(() -> new UnsupportedOperationException("Entity doesn't have ID"));
    }

    private String getTableName(Class entity) {

        String name;

        name = ((Entity)entity.getAnnotation(Entity.class)).name();

        if (!name.trim().isEmpty()){
            return name;
        }else {
            return entity.getSimpleName().toLowerCase()+"s";
        }
    }

    private String generateInsertQuery(E entity, String tableName){
        StringBuilder query = new StringBuilder();

        query.append("INSERT INTO `").append(tableName).append("`").append("(");

        query.append(getColumnNames(entity)).append(" )");

        query.append(" VALUES ( ");

        try {
            query.append(getColumnValues(entity)).append(" );");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return query.toString();
    }

    private String getColumnValues(E entity) throws IllegalAccessException {
        StringBuilder columnsValues = new StringBuilder();

        Field[] declaredFields = entity.getClass().getDeclaredFields();

        for (int i = 0; i < declaredFields.length; i++) {

            Field field = declaredFields[i];
            field.setAccessible(true);

            Object fieldValue = field.get(entity);

            if (!(field.isAnnotationPresent(Id.class))) {
                try {
                    String value = field.get(entity).toString();

                    if (fieldValue instanceof Date) {

                        columnsValues.append("'")
                                .append(new SimpleDateFormat("yyyy-MM-dd")
                                        .format(fieldValue)).append("'");
                    } else if (fieldValue instanceof String){
                        columnsValues.append("'").append(fieldValue).append("'");
                    }
                    else {
                        columnsValues.append(value);
                    }

                    if (i<declaredFields.length-1){
                        columnsValues.append(", ");
                    }

                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return columnsValues.toString();
    }

    private String getColumnNames(E entity) {

        StringBuilder columns = new StringBuilder();

        Field[] declaredFields = entity.getClass().getDeclaredFields();

        for (int i = 0; i < declaredFields.length; i++) {

            Field field = declaredFields[i];
            field.setAccessible(true);

            if (!(field.isAnnotationPresent(Id.class))) {

                columns.append(field.getName());

                if (i<declaredFields.length-1){
                    columns.append(", ");
                }

            }
        }
        return columns.toString();
    }
}
