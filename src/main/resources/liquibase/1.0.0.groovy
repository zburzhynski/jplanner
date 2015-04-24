databaseChangeLog {

    changeSet(id: '2013-04-24-1', author: 'Vladimir Zburzhynski <zburzhynski@gmail.com>') {
        comment("Create jplanner scheme")
        sql(stripComments: true, splitStatements: false, endDelimiter: ';') {
            "create schema jplanner authorization jplanner"
        }
    }

    changeSet(id: '2013-04-24-2', author: 'Vladimir Zburzhynski <zburzhynski@gmail.com>') {
        createTable(schemaName: 'jplanner', tableName: 'schedule', tablespace: 'jplanner_data', remarks: 'Contains schedule events') {
            column(name: 'id', type: 'VARCHAR(128)', remarks: 'The unique identifier of schedule event') {
                constraints(nullable: false)
            }
            column(name: 'start_date', type: 'TIMESTAMP', defaultValue: 'now()', remarks: 'Start date of schedule event') {
                constraints(nullable: false)
            }
            column(name: 'end_date', type: 'TIMESTAMP', defaultValue: 'now()', remarks: 'Start date of schedule event') {
                constraints(nullable: false)
            }
            column(name: 'title', type: 'VARCHAR(200)', defaultValue: '', remarks: 'Title of schedule event') {
                constraints(nullable: false)
            }
            column(name: 'description', type: 'VARCHAR(100)', defaultValue: '', remarks: 'Description of schedule event')
        }
        addPrimaryKey(schemaName: 'jplanner', tableName: 'schedule', tablespace: 'jplanner_index',
                columnNames: 'id', constraintName: 'PK_schedule')
    }

}