db.topLevelItems.update({ "publisher" : "lovefilm.com" }, {$set : { "lastUpdated" : ISODate("2013-03-01T00:00:00.000Z") }, $set : { "thisOrChildLastUpdated" : ISODate("2013-03-01T00:00:00.000Z") }}, false, true);
db.children.update({ "publisher" : "lovefilm.com" }, {$set : { "lastUpdated" : ISODate("2013-03-01T00:00:00.000Z") }, $set : { "thisOrChildLastUpdated" : ISODate("2013-03-01T00:00:00.000Z") }}, false, true);
db.containers.update({ "publisher" : "lovefilm.com" }, {$set : { "lastUpdated" : ISODate("2013-03-01T00:00:00.000Z") }, $set : { "thisOrChildLastUpdated" : ISODate("2013-03-01T00:00:00.000Z") }}, false, true);