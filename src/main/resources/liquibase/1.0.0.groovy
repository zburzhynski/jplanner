databaseChangeLog {

    changeSet(id: '2015-04-24-1', author: 'Vladimir Zburzhynski <zburzhynski@gmail.com>') {
        comment("Create jplanner scheme")
        sql(stripComments: true, splitStatements: false, endDelimiter: ';') {
            "create schema jplanner authorization jplanner"
        }
    }

    changeSet(id: '2015-04-30-2', author: 'Mikalai Karabeika <hexed2@gmail.com>') {
        createTable(schemaName: 'jplanner', tableName: 'cabinet', tablespace: 'jplanner_data', remarks: 'Reference of cabinet') {
            column(name: 'id', type: 'VARCHAR(128)', remarks: 'The unique identifier of cabinet') {
                constraints(nullable: false)
            }
            column(name: 'number', type: 'VARCHAR(20)', defaultValue: '', remarks: 'The number of cabinet') {
                constraints(nullable: false)
            }
            column(name: 'name', type: 'VARCHAR(100)', defaultValue: '', remarks: 'The cabinet name') {
                constraints(nullable: false)
            }
            column(name: 'description', type: 'VARCHAR(500)', defaultValue: '', remarks: 'The cabinet description')
        }
        addPrimaryKey(schemaName: 'jplanner', tableName: 'cabinet', tablespace: 'jplanner_index',
                columnNames: 'id', constraintName: 'PK_cabinet')
    }

    changeSet(id: '2015-04-30-3', author: 'Mikalai Karabeika <hexed2@gmail.com>') {
        createTable(schemaName: 'jplanner', tableName: 'workplace', tablespace: 'jplanner_data', remarks: 'Reference of workplace') {
            column(name: 'id', type: 'VARCHAR(128)', remarks: 'The unique identifier of workplace') {
                constraints(nullable: false)
            }
            column(name: 'cabinet_id', type: 'VARCHAR(128)', remarks: 'The reference to the cabinet table') {
                constraints(nullable: false)
            }
            column(name: 'name', type: 'VARCHAR(100)', defaultValue: '', remarks: 'The workplace name') {
                constraints(nullable: false)
            }
            column(name: 'description', type: 'VARCHAR(500)', defaultValue: '', remarks: 'The workplace description')
        }
        addPrimaryKey(schemaName: 'jplanner', tableName: 'workplace', tablespace: 'jplanner_index',
                columnNames: 'id', constraintName: 'PK_workplace')
    }

    changeSet(id: '2015-04-30-4', author: 'Mikalai Karabeika <hexed2@gmail.com>') {
        addForeignKeyConstraint(constraintName: 'FK_workplace_2_cabinet',
                baseTableName: 'workplace', baseTableSchemaName: 'jplanner', baseColumnNames: 'cabinet_id',
                referencedTableName: 'cabinet', referencedTableSchemaName: 'jplanner', referencedColumnNames: 'id')
    }

    changeSet(id: '2015-04-30-5', author: 'Vladimir Zburzhynski <zburzhynski@gmail.com>') {
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

    changeSet(id: '2015-04-30-6', author: 'Vladimir Zburzhynski <zburzhynski@gmail.com>') {
        createTable(schemaName: 'jplanner', tableName: 'job_position', tablespace: 'jplanner_data', remarks: 'Reference job position') {
            column(name: 'id', type: 'VARCHAR(128)', remarks: 'The unique identifier of job position') {
                constraints(nullable: false)
            }
            column(name: 'name', type: 'VARCHAR(500)', defaultValue: '', remarks: 'The job position name') {
                constraints(nullable: false)
            }
            column(name: 'position_type', type: 'VARCHAR(20)', defaultValue: '', remarks: 'The job position type')
        }
        addPrimaryKey(schemaName: 'jplanner', tableName: 'job_position', tablespace: 'jplanner_index',
                columnNames: 'id', constraintName: 'PK_job_position')
    }

    changeSet(id: '2015-04-30-7', author: 'Vladimir Zburzhynski <zburzhynski@gmail.com>') {
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

    changeSet(id: '2015-04-30-8', author: 'Vladimir Zburzhynski <zburzhynski@gmail.com>') {
        addForeignKeyConstraint(constraintName: 'FK_employee_2_person',
                baseTableName: 'employee', baseTableSchemaName: 'jplanner', baseColumnNames: 'person_id',
                referencedTableName: 'person', referencedTableSchemaName: 'jplanner', referencedColumnNames: 'id')
    }

    changeSet(id: '2015-04-30-9', author: 'Vladimir Zburzhynski <zburzhynski@gmail.com>') {
        addForeignKeyConstraint(constraintName: 'FK_employee_2_job_position',
                baseTableName: 'employee', baseTableSchemaName: 'jplanner', baseColumnNames: 'job_position_id',
                referencedTableName: 'job_position', referencedTableSchemaName: 'jplanner', referencedColumnNames: 'id')
    }

    changeSet(id: '2015-04-30-10', author: 'Vladimir Zburzhynski <zburzhynski@gmail.com>') {
        createTable(schemaName: 'jplanner', tableName: 'schedule', tablespace: 'jplanner_data', remarks: 'Contains schedule events') {
            column(name: 'id', type: 'VARCHAR(128)', remarks: 'The unique identifier of schedule event') {
                constraints(nullable: false)
            }
            column(name: 'workplace_id', type: 'VARCHAR(128)', remarks: 'The reference to the workplace table') {
                constraints(nullable: false)
            }
            column(name: 'patient_id', type: 'VARCHAR(128)', remarks: 'The reference to the patient table')
            column(name: 'person_id', type: 'VARCHAR(128)', remarks: 'The reference to the person table') {
                constraints(nullable: false)
            }
            column(name: 'schedule_status', type: 'VARCHAR(10)', defaultValue: '', remarks: 'Schedule status') {
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

    changeSet(id: '2015-04-30-11', author: 'Vladimir Zburzhynski <zburzhynski@gmail.com>') {
        addForeignKeyConstraint(constraintName: 'FK_schedule_2_workplace',
                baseTableName: 'schedule', baseTableSchemaName: 'jplanner', baseColumnNames: 'workplace_id',
                referencedTableName: 'workplace', referencedTableSchemaName: 'jplanner', referencedColumnNames: 'id')
    }

    changeSet(id: '2015-04-30-12', author: 'Vladimir Zburzhynski <zburzhynski@gmail.com>') {
        addForeignKeyConstraint(constraintName: 'FK_schedule_2_person',
                baseTableName: 'schedule', baseTableSchemaName: 'jplanner', baseColumnNames: 'person_id',
                referencedTableName: 'person', referencedTableSchemaName: 'jplanner', referencedColumnNames: 'id')
    }

    changeSet(id: '2015-04-30-13', author: 'Vladimir Zburzhynski <zburzhynski@gmail.com>') {
        addForeignKeyConstraint(constraintName: 'FK_doctor_2_employee',
                baseTableName: 'schedule', baseTableSchemaName: 'jplanner', baseColumnNames: 'doctor_id',
                referencedTableName: 'employee', referencedTableSchemaName: 'jplanner', referencedColumnNames: 'id')
    }

    changeSet(id: '2015-06-09-1', author: 'Vladimir Zburzhynski <zburzhynski@gmail.com>') {
        createTable(schemaName: 'jplanner', tableName: 'setting', tablespace: 'jplanner_data', remarks: 'Application settings') {
            column(name: 'id', type: 'VARCHAR(128)', remarks: 'The unique identifier of setting') {
                constraints(nullable: false)
            }
            column(name: 'category', type: 'VARCHAR(30)', remarks: 'The setting category') {
                constraints(nullable: false)
            }
            column(name: 'name', type: 'VARCHAR(50)', remarks: 'The setting name') {
                constraints(nullable: false)
            }
            column(name: 'value', type: 'VARCHAR(250)', remarks: 'The setting value') {
                constraints(nullable: false)
            }
            column(name: 'type', type: 'VARCHAR(15)', remarks: 'The setting value type') {
                constraints(nullable: false)
            }
            column(name: 'description', type: 'VARCHAR(300)', remarks: 'The setting description') {
                constraints(nullable: false)
            }
            column(name: 'sort_order', type: 'VARCHAR(15)', remarks: 'The setting sort order') {
                constraints(nullable: false)
            }
        }
        addPrimaryKey(schemaName: 'jplanner', tableName: 'setting', tablespace: 'jplanner_index',
                columnNames: 'id', constraintName: 'PK_setting')
    }

    changeSet(id: '2015-06-09-2', author: 'Vladimir Zburzhynski <zburzhynski@gmail.com>') {
        insert(schemaName: 'jplanner', tableName: 'setting') {
            column(name: 'id', value: 'a5e0e9f9-98b0-4cfa-a8ac-8adbe4c6afff')
            column(name: 'category', value: 'JDENT')
            column(name: 'name', value: 'jdent_integration_enabled')
            column(name: 'value', value: 'false')
            column(name: 'type', value: 'BOOLEAN')
            column(name: 'description', value: 'Интеграция с jDent')
            column(name: 'sort_order', value: '1')
        }
        insert(schemaName: 'jplanner', tableName: 'setting') {
            column(name: 'id', value: '6bfc2406-e1cf-4992-91ce-989bcc5f4ff0')
            column(name: 'category', value: 'JDENT')
            column(name: 'name', value: 'jdent_url')
            column(name: 'value', value: 'http://localhost:8080/jdent/')
            column(name: 'type', value: 'STRING')
            column(name: 'description', value: 'Ссылка на jDent')
            column(name: 'sort_order', value: '2')
        }
        insert(schemaName: 'jplanner', tableName: 'setting') {
            column(name: 'id', value: '5728e886-d578-4409-bcd6-f255dc9b030d')
            column(name: 'category', value: 'VIEW')
            column(name: 'name', value: 'patients_per_page')
            column(name: 'value', value: '20')
            column(name: 'type', value: 'INTEGER')
            column(name: 'description', value: 'Количество пациентов на странице')
            column(name: 'sort_order', value: '1')
        }
        insert(schemaName: 'jplanner', tableName: 'setting') {
            column(name: 'id', value: '56e55e4d-36c6-4805-94b2-e35b024b1eeb')
            column(name: 'category', value: 'VIEW')
            column(name: 'name', value: 'employees_per_page')
            column(name: 'value', value: '20')
            column(name: 'type', value: 'INTEGER')
            column(name: 'description', value: 'Количество сотрудников на странице')
            column(name: 'sort_order', value: '2')
        }
        insert(schemaName: 'jplanner', tableName: 'setting') {
            column(name: 'id', value: '7e578ea3-b260-42c3-b148-19d8ee18bae5')
            column(name: 'category', value: 'VIEW')
            column(name: 'name', value: 'cabinets_per_page')
            column(name: 'value', value: '20')
            column(name: 'type', value: 'INTEGER')
            column(name: 'description', value: 'Количество кабинетов на странице')
            column(name: 'sort_order', value: '3')
        }
        insert(schemaName: 'jplanner', tableName: 'setting') {
            column(name: 'id', value: '594be841-a8f3-4ffa-83bc-51149240465a')
            column(name: 'category', value: 'VIEW')
            column(name: 'name', value: 'job_positions_per_page')
            column(name: 'value', value: '20')
            column(name: 'type', value: 'INTEGER')
            column(name: 'description', value: 'Количество должностей на странице')
            column(name: 'sort_order', value: '4')
        }
    }

}