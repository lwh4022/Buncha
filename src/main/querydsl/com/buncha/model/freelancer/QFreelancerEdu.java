package com.buncha.model.freelancer;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QFreelancerEdu is a Querydsl query type for FreelancerEdu
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QFreelancerEdu extends EntityPathBase<FreelancerEdu> {

    private static final long serialVersionUID = 381523373L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QFreelancerEdu freelancerEdu = new QFreelancerEdu("freelancerEdu");

    public final DateTimePath<java.util.Date> changed = createDateTime("changed", java.util.Date.class);

    public final NumberPath<Long> eduSeq = createNumber("eduSeq", Long.class);

    public final StringPath enrollYear = createString("enrollYear");

    public final QFreelancer freelancer;

    public final StringPath gradYear = createString("gradYear");

    public final BooleanPath isStudent = createBoolean("isStudent");

    public final StringPath major = createString("major");

    public final EnumPath<com.buncha.model.enumClass.PoOrUnderType> postUnder = createEnum("postUnder", com.buncha.model.enumClass.PoOrUnderType.class);

    public final StringPath schoolName = createString("schoolName");

    public final NumberPath<Double> score = createNumber("score", Double.class);

    public final StringPath subMajor = createString("subMajor");

    public final EnumPath<com.buncha.model.enumClass.SubMajorType> subMajorType = createEnum("subMajorType", com.buncha.model.enumClass.SubMajorType.class);

    public final StringPath thesis = createString("thesis");

    public final NumberPath<Double> totalScore = createNumber("totalScore", Double.class);

    public QFreelancerEdu(String variable) {
        this(FreelancerEdu.class, forVariable(variable), INITS);
    }

    public QFreelancerEdu(Path<? extends FreelancerEdu> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QFreelancerEdu(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QFreelancerEdu(PathMetadata metadata, PathInits inits) {
        this(FreelancerEdu.class, metadata, inits);
    }

    public QFreelancerEdu(Class<? extends FreelancerEdu> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.freelancer = inits.isInitialized("freelancer") ? new QFreelancer(forProperty("freelancer"), inits.get("freelancer")) : null;
    }

}

