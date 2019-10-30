package com.buncha.model.job;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QJobLocation is a Querydsl query type for JobLocation
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QJobLocation extends EntityPathBase<JobLocation> {

    private static final long serialVersionUID = -1463908260L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QJobLocation jobLocation = new QJobLocation("jobLocation");

    public final QJob job;

    public final NumberPath<Long> jobLocationSeq = createNumber("jobLocationSeq", Long.class);

    public final EnumPath<com.buncha.model.job.enumClass.LocationType> location = createEnum("location", com.buncha.model.job.enumClass.LocationType.class);

    public QJobLocation(String variable) {
        this(JobLocation.class, forVariable(variable), INITS);
    }

    public QJobLocation(Path<? extends JobLocation> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QJobLocation(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QJobLocation(PathMetadata metadata, PathInits inits) {
        this(JobLocation.class, metadata, inits);
    }

    public QJobLocation(Class<? extends JobLocation> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.job = inits.isInitialized("job") ? new QJob(forProperty("job"), inits.get("job")) : null;
    }

}

