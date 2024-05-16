package com.example.accountbank.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QShoppingItemEntity is a Querydsl query type for ShoppingItemEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QShoppingItemEntity extends EntityPathBase<ShoppingItemEntity> {

    private static final long serialVersionUID = 992423283L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QShoppingItemEntity shoppingItemEntity = new QShoppingItemEntity("shoppingItemEntity");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final NumberPath<Integer> coupangPrice = createNumber("coupangPrice", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Integer> marketPrice = createNumber("marketPrice", Integer.class);

    public final NumberPath<Integer> martPrice = createNumber("martPrice", Integer.class);

    public final StringPath name = createString("name");

    public final QShoppingEntity shopping;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QShoppingItemEntity(String variable) {
        this(ShoppingItemEntity.class, forVariable(variable), INITS);
    }

    public QShoppingItemEntity(Path<? extends ShoppingItemEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QShoppingItemEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QShoppingItemEntity(PathMetadata metadata, PathInits inits) {
        this(ShoppingItemEntity.class, metadata, inits);
    }

    public QShoppingItemEntity(Class<? extends ShoppingItemEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.shopping = inits.isInitialized("shopping") ? new QShoppingEntity(forProperty("shopping")) : null;
    }

}

