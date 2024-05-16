package com.example.accountbank.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QShoppingEntity is a Querydsl query type for ShoppingEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QShoppingEntity extends EntityPathBase<ShoppingEntity> {

    private static final long serialVersionUID = 69957376L;

    public static final QShoppingEntity shoppingEntity = new QShoppingEntity("shoppingEntity");

    public final QBaseEntity _super = new QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ListPath<ShoppingItemEntity, QShoppingItemEntity> shoppingItems = this.<ShoppingItemEntity, QShoppingItemEntity>createList("shoppingItems", ShoppingItemEntity.class, QShoppingItemEntity.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QShoppingEntity(String variable) {
        super(ShoppingEntity.class, forVariable(variable));
    }

    public QShoppingEntity(Path<? extends ShoppingEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QShoppingEntity(PathMetadata metadata) {
        super(ShoppingEntity.class, metadata);
    }

}

