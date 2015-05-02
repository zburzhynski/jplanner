databaseChangeLog {

    changeSet(id: '2015-04-24-1', author: 'Vladimir Zburzhynski <zburzhynski@gmail.com>') {
        comment("Create jplanner scheme")
        sql(stripComments: true, splitStatements: false, endDelimiter: ';') {
            "create schema jplanner authorization jplanner"
        }
    }

    changeSet(id: '2015-04-30-2', author: 'Vladimir Zburzhynski <zburzhynski@gmail.com>') {
        createTable(schemaName: 'jplanner', tableName: 'person', tablespace: 'jplanner_data', remarks: 'Reference of person') {
            column(name: 'id', type: 'VARCHAR(128)', remarks: 'The unique identifier of person') {
                constraints(nullable: false)
            }
            column(name: 'name', type: 'VARCHAR(50)', defaultValue: '', remarks: 'The person name') {
                constraints(nullable: false)
            }
            column(name: 'surname', type: 'VARCHAR(50)', defaultValue: '', remarks: 'The person surname') {
                constraints(nullable: false)
            }
            column(name: 'patronymic', type: 'VARCHAR(50)', defaultValue: '', remarks: 'The person patronymic') {
                constraints(nullable: false)
            }
            column(name: 'birthday', type: 'DATE', defaultValue: 'now()', remarks: 'The person birthday') {
                constraints(nullable: false)
            }
            column(name: 'gender', type: 'CHAR(1)', defaultValue: '', remarks: 'The person sex') {
                constraints(nullable: false)
            }
        }
        addPrimaryKey(schemaName: 'jplanner', tableName: 'person', tablespace: 'jplanner_index',
                columnNames: 'id', constraintName: 'PK_person')
    }

    changeSet(id: '2015-04-30-3', author: 'Vladimir Zburzhynski <zburzhynski@gmail.com>') {
        createTable(schemaName: 'jplanner', tableName: 'job_position', tablespace: 'jplanner_data', remarks: 'Reference job position') {
            column(name: 'id', type: 'VARCHAR(128)', remarks: 'The unique identifier of job position') {
                constraints(nullable: false)
            }
            column(name: 'name', type: 'VARCHAR(500)', defaultValue: '', remarks: 'The job position name') {
                constraints(nullable: false)
            }
            column(name: 'position_type', type: 'VARCHAR(20)', defaultValue: '', remarks: 'The job position type') {
                constraints(nullable: false)
            }
        }
        addPrimaryKey(schemaName: 'jplanner', tableName: 'job_position', tablespace: 'jplanner_index',
                columnNames: 'id', constraintName: 'PK_job_position')
    }

    changeSet(id: '2015-04-30-4', author: 'Vladimir Zburzhynski <zburzhynski@gmail.com>') {
        createTable(schemaName: 'jplanner', tableName: 'employee', tablespace: 'jplanner_data', remarks: 'Employee of clinic') {
            column(name: 'id', type: 'VARCHAR(128)', remarks: 'The unique identifier of employee') {
                constraints(nullable: false)
            }
            column(name: 'person_id', type: 'VARCHAR(128)', remarks: 'The reference to the person table') {
                constraints(nullable: false)
            }
            column(name: 'job_position_id', type: 'VARCHAR(128)', remarks: 'The reference to the job position table') {
                constraints(nullable: false)
            }
            column(name: 'email', type: 'VARCHAR(50)', defaultValue: '', remarks: 'The employee email')
            column(name: 'additional_info', type: 'VARCHAR(500)', defaultValue: '', remarks: 'The additional information')

        }
        addPrimaryKey(schemaName: 'jplanner', tableName: 'employee', tablespace: 'jplanner_index',
                columnNames: 'id', constraintName: 'PK_employee')
    }

    changeSet(id: '2015-04-30-5', author: 'Vladimir Zburzhynski <zburzhynski@gmail.com>') {
        addForeignKeyConstraint(constraintName: 'FK_employee_2_person',
                baseTableName: 'employee', baseTableSchemaName: 'jplanner', baseColumnNames: 'person_id',
                referencedTableName: 'person', referencedTableSchemaName: 'jplanner', referencedColumnNames: 'id')
    }

    changeSet(id: '2015-04-30-6', author: 'Vladimir Zburzhynski <zburzhynski@gmail.com>') {
        addForeignKeyConstraint(constraintName: 'FK_employee_2_job_position',
                baseTableName: 'employee', baseTableSchemaName: 'jplanner', baseColumnNames: 'job_position_id',
                referencedTableName: 'job_position', referencedTableSchemaName: 'jplanner', referencedColumnNames: 'id')
    }

    changeSet(id: '2015-04-30-7', author: 'Vladimir Zburzhynski <zburzhynski@gmail.com>') {
        createTable(schemaName: 'jplanner', tableName: 'schedule', tablespace: 'jplanner_data', remarks: 'Contains schedule events') {
            column(name: 'id', type: 'VARCHAR(128)', remarks: 'The unique identifier of schedule event') {
                constraints(nullable: false)
            }
            column(name: 'person_id', type: 'VARCHAR(128)', remarks: 'The reference to the person table') {
                constraints(nullable: false)
            }
            column(name: 'doctor_id', type: 'VARCHAR(128)', remarks: 'The reference to the employee table') {
                constraints(nullable: false)
            }
            column(name: 'start_date', type: 'TIMESTAMP', defaultValue: 'now()', remarks: 'Start date of schedule event') {
                constraints(nullable: false)
            }
            column(name: 'end_date', type: 'TIMESTAMP', defaultValue: 'now()', remarks: 'Start date of schedule event') {
                constraints(nullable: false)
            }
            column(name: 'title', type: 'VARCHAR(1100)', defaultValue: '', remarks: 'Title of schedule event') {
                constraints(nullable: false)
            }
            column(name: 'complaint', type: 'VARCHAR(1000)', defaultValue: '', remarks: 'Complaint of patient')
            column(name: 'additional_info', type: 'VARCHAR(1000)', defaultValue: '', remarks: 'Additional information of schedule event')
        }
        addPrimaryKey(schemaName: 'jplanner', tableName: 'schedule', tablespace: 'jplanner_index',
                columnNames: 'id', constraintName: 'PK_schedule')
    }

    changeSet(id: '2015-04-30-8', author: 'Vladimir Zburzhynski <zburzhynski@gmail.com>') {
        addForeignKeyConstraint(constraintName: 'FK_schedule_2_person',
                baseTableName: 'schedule', baseTableSchemaName: 'jplanner', baseColumnNames: 'person_id',
                referencedTableName: 'person', referencedTableSchemaName: 'jplanner', referencedColumnNames: 'id')
    }

    changeSet(id: '2015-04-30-9', author: 'Vladimir Zburzhynski <zburzhynski@gmail.com>') {
        addForeignKeyConstraint(constraintName: 'FK_doctor_2_employee',
                baseTableName: 'schedule', baseTableSchemaName: 'jplanner', baseColumnNames: 'doctor_id',
                referencedTableName: 'employee', referencedTableSchemaName: 'jplanner', referencedColumnNames: 'id')
    }

}