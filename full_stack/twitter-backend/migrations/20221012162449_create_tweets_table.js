/**
 * @param { import("knex").Knex } knex
 * @returns { Promise<void> }
 */
exports.up = function(knex) {
    return knex.schema.createTable('tweets',function(table){
        table.increments('id');//primary key auto increments
        table.string('name');
        table.string('description',255)
    })
    
  
};

/**
 * @param { import("knex").Knex } knex
 * @returns { Promise<void> }
 */
exports.down = function(knex) {
    return knex.schema.dropTable('tweets')
  
};
