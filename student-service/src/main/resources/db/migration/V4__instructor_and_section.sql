create table if not exists instructor (
                                          id bigserial primary key,
                                          first_name varchar(100) not null,
    last_name  varchar(100) not null,
    email      varchar(150) unique not null
    );

-- Her "section" belirli bir dersin belirli dönemde açılmış şubesidir
create table if not exists section (
                                       id bigserial primary key,
                                       course_id bigint not null references course(id) on delete cascade,
    instructor_id bigint references instructor(id),
    semester varchar(40) not null,         -- ör: 'Fall 2025'
    capacity int not null default 40,      -- basit kapasite
    schedule varchar(120)                  -- ör: 'Mon 10:00-12:00 / R-101'
    );

create index if not exists idx_section_course on section(course_id);
create unique index if not exists uq_section_unique
    on section(course_id, semester, instructor_id, schedule);
