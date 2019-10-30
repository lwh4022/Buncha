package com.buncha.model.job;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAppliedJob is a Querydsl query type for AppliedJob
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QAppliedJob extends EntityPathBase<AppliedJob> {

    private static final long serialVersionUID = 1696657142L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAppliedJob appliedJob = new QAppliedJob("appliedJob");

    public final NumberPath<Long> appliedJobSeq = createNumber("appliedJobSeq", Long.class);

    public final DateTimePath<java.util.Date> changed = createDateTime("changed", java.util.Date.class);

    public final com.buncha.model.freelancer.QFreelancer freelancer;

    public final QJob job;

    public QAppliedJob(String variable) {
        this(AppliedJob.class, forVariable(variable), INITS);
    }

    public QAppliedJob(Path<? extends AppliedJob> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAppliedJob(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAppliedJob(PathMetadata metadata, PathInits inits) {
        this(AppliedJob.class, metadata, inits);
    }

    public QAppliedJob(Class<? extends AppliedJob> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.freelancer = inits.isInitialized("freelancer") ? new com.buncha.model.freelancer.QFreelancer(forProperty("freelancer"), inits.get("freelancer")) : null;
        this.job = inits.isInitialized("job") ? new QJob(forProperty("job"), inits.get("job")) : null;
    }

}

