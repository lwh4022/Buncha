package com.buncha.model.freelancer;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QFreelancerForeignLang is a Querydsl query type for FreelancerForeignLang
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QFreelancerForeignLang extends EntityPathBase<FreelancerForeignLang> {

    private static final long serialVersionUID = -871291943L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QFreelancerForeignLang freelancerForeignLang = new QFreelancerForeignLang("freelancerForeignLang");

    public final StringPath cerOrCon = createString("cerOrCon");

    public final DateTimePath<java.util.Date> changed = createDateTime("changed", java.util.Date.class);

    public final NumberPath<Long> foreignLangSeq = createNumber("foreignLangSeq", Long.class);

    public final QFreelancer freelancer;

    public final StringPath gotDate = createString("gotDate");

    public final StringPath language = createString("language");

    public final EnumPath<com.buncha.model.enumClass.LevelConType> levelConType = createEnum("levelConType", com.buncha.model.enumClass.LevelConType.class);

    public final NumberPath<Double> score = createNumber("score", Double.class);

    public final StringPath testName = createString("testName");

    public final NumberPath<Double> totalScore = createNumber("totalScore", Double.class);

    public final EnumPath<com.buncha.model.enumClass.UnitType> unitType = createEnum("unitType", com.buncha.model.enumClass.UnitType.class);

    public QFreelancerForeignLang(String variable) {
        this(FreelancerForeignLang.class, forVariable(variable), INITS);
    }

    public QFreelancerForeignLang(Path<? extends FreelancerForeignLang> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QFreelancerForeignLang(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QFreelancerForeignLang(PathMetadata metadata, PathInits inits) {
        this(FreelancerForeignLang.class, metadata, inits);
    }

    public QFreelancerForeignLang(Class<? extends FreelancerForeignLang> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.freelancer = inits.isInitialized("freelancer") ? new QFreelancer(forProperty("freelancer"), inits.get("freelancer")) : null;
    }

}

