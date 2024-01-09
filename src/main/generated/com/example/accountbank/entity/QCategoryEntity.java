package com.example.accountbank.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QCategoryEntity is a Querydsl query type for CategoryEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCategoryEntity extends EntityPathBase<CategoryEntity> {

    private static final long serialVersionUID = 1545124374L;

    public static final QCategoryEntity categoryEntity = new QCategoryEntity("categoryEntity");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final StringPath categoryIcon = createString("categoryIcon");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QCategoryEntity(String variable) {
        super(CategoryEntity.class, forVariable(variable));
    }

    public QCategoryEntity(Path<? extends CategoryEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCategoryEntity(PathMetadata metadata) {
        super(CategoryEntity.class, metadata);
    }

}

