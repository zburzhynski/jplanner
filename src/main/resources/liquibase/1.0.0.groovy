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
        createTable(schemaName: 'jplanner', tableName: 'schedule', tablespace: 'jplanner_data', remarks: 'Contains schedule events') {
            column(name: 'id', type: 'VARCHAR(128)', remarks: 'The unique identifier of schedule event') {
                constraints(nullable: false)
            }
            column(name: 'person_id', type: 'VARCHAR(128)', remarks: 'The reference to the person table') {
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
            column(name: 'description', type: 'VARCHAR(1000)', defaultValue: '', remarks: 'Description of schedule event')
        }
        addPrimaryKey(schemaName: 'jplanner', tableName: 'schedule', tablespace: 'jplanner_index',
                columnNames: 'id', constraintName: 'PK_schedule')
    }

    changeSet(id: '2015-04-30-4', author: 'Vladimir Zburzhynski <zburzhynski@gmail.com>') {
        addForeignKeyConstraint(constraintName: 'FK_schedule_2_person',
                baseTableName: 'schedule', baseTableSchemaName: 'jplanner', baseColumnNames: 'person_id',
                referencedTableName: 'person', referencedTableSchemaName: 'jplanner', referencedColumnNames: 'id')
    }

}