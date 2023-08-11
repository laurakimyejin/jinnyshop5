package com.jinnyshop5.product.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QProduct is a Querydsl query type for Product
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProduct extends EntityPathBase<Product> {

    private static final long serialVersionUID = -1375231905L;

    public static final QProduct product = new QProduct("product");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath image = createString("image");

    public final NumberPath<Integer> price = createNumber("price", Integer.class);

    public final StringPath productDetail = createString("productDetail");

    public final StringPath productName = createString("productName");

    public final EnumPath<com.jinnyshop5.product.constant.ProductStatus> productStatus = createEnum("productStatus", com.jinnyshop5.product.constant.ProductStatus.class);

    public final DateTimePath<java.time.LocalDateTime> regTime = createDateTime("regTime", java.time.LocalDateTime.class);

    public final NumberPath<Integer> sales = createNumber("sales", Integer.class);

    public final NumberPath<Integer> stockNumber = createNumber("stockNumber", Integer.class);

    public final DateTimePath<java.time.LocalDateTime> updateTime = createDateTime("updateTime", java.time.LocalDateTime.class);

    public QProduct(String variable) {
        super(Product.class, forVariable(variable));
    }

    public QProduct(Path<? extends Product> path) {
        super(path.getType(), path.getMetadata());
    }

    public QProduct(PathMetadata metadata) {
        super(Product.class, metadata);
    }

}

