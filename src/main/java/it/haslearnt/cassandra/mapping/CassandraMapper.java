package it.haslearnt.cassandra.mapping;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.LinkedList;
import java.util.List;

import org.apache.cassandra.thrift.Column;
import org.scale7.cassandra.pelops.Mutator;

public class CassandraMapper {

    protected List<Column> entityColumns(Object entity, Mutator mutator) throws IllegalAccessException {
        List<Column> columns = new LinkedList<Column>();
        List<EntityField> result = getFieldsAnnotatedBy(it.haslearnt.cassandra.mapping.Column.class, entity.getClass());
        for (EntityField field : result) {
            columns.add(
                    mutator.newColumn(columnName(field), field.getValueFor(entity).toString())
                    );
        }
        return columns;
    }

    private String columnName(EntityField field) {
        String annotationValue = field.getAnnotationValue(it.haslearnt.cassandra.mapping.Column.class);

        if ("".equals(annotationValue)) {
            annotationValue = field.name();
        }

        return annotationValue;
    }

    protected String entityId(Object entity) {
        for (EntityField field : getFieldsAnnotatedBy(Id.class, entity.getClass())) {
            return (String) field.getValueFor(entity);
        }
        throw new RuntimeException("Entity has no id");
    }

    @SuppressWarnings("unchecked")
    protected String entityName() {
        Class<? extends EntityWithGeneratedId> entityClass = (Class<? extends EntityWithGeneratedId>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
        if (annotationHasEntityName(entityClass)) {
            return entityClass.getAnnotation(Entity.class).value();
        }

        return entityClass.getSimpleName();
    }

    private boolean annotationHasEntityName(Class<? extends EntityWithGeneratedId> entityClass) {
        return entityClass.isAnnotationPresent(Entity.class)
                && !"".equals(entityClass.getAnnotation(Entity.class).value());
    }

    protected List<EntityField> getFieldsAnnotatedBy(Class<? extends Annotation> annotation, Class<?> clazz) {
        List<EntityField> result = new LinkedList<EntityField>();
        Class<?> currentClass = clazz;
        do {
            Field[] fields = currentClass.getDeclaredFields();
            for (Field field : fields) {
                if (field.isAnnotationPresent(annotation)) {
                    result.add(new EntityField(field));
                }
            }
            currentClass = currentClass.getSuperclass();
        } while (currentClass != null);

        return result;
    }
}