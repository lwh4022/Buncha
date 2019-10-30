package com.buncha.model.job;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QJob is a Querydsl query type for Job
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QJob extends EntityPathBase<Job> {

    private static final long serialVersionUID = 313064327L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QJob job = new QJob("job");

    public final NumberPath<Integer> amount = createNumber("amount", Integer.class);

    public final DateTimePath<java.util.Date> changed = createDateTime("changed", java.util.Date.class);

    public final StringPath comOrIndi = createString("comOrIndi");

    public final StringPath companyName = createString("companyName");

    public final StringPath email = createString("email");

    public final StringPath endDate = createString("endDate");

    public final BooleanPath isNego = createBoolean("isNego");

    public final SimplePath<java.sql.Clob> jobDescription = createSimple("jobDescription", java.sql.Clob.class);

    public final NumberPath<Long> jobSeq = createNumber("jobSeq", Long.class);

    public final StringPath jobType = createString("jobType");

    public final com.buncha.model.member.QMember member;

    public final NumberPath<Integer> numRecurit = createNumber("numRecurit", Integer.class);

    public final StringPath phone = createString("phone");

    public final StringPath pic = createString("pic");

    public final StringPath recruitDate = createString("recruitDate");

    public final NumberPath<Long> recruitDateTime = createNumber("recruitDateTime", Long.class);

    public final StringPath salary = createString("salary");

    public final SimplePath<java.sql.Clob> singleLineIntro = createSimple("singleLineIntro", java.sql.Clob.class);

    public final StringPath startDate = createString("startDate");

    public final NumberPath<Long> startDateTime = createNumber("startDateTime", Long.class);

    public QJob(String variable) {
        this(Job.class, forVariable(variable), INITS);
    }

    public QJob(Path<? extends Job> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QJob(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QJob(PathMetadata metadata, PathInits inits) {
        this(Job.class, metadata, inits);
    }

    public QJob(Class<? extends Job> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new com.buncha.model.member.QMember(forProperty("member")) : null;
    }

}

