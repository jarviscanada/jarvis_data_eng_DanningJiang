const express = require('express')
const knex = require('knex')
const knexConfig = require('../knexfile')
const db = knex(knexConfig)
const router = express.Router()

//This is the router module

router.get('/tweets',async(req,res)=>{
    try{
        const tweets = await db.raw(`SELECT * FROM tweets;`)
        console.log('DB data tweets',tweets)
        res.json(tweets.rows)

    } catch(err){
        console.log(err)
        res.json(err)
    }
   
})


router.post('/tweets',async(req,res) => {
    
    console.log('This is the request body:',req.body)
    const id = Math.floor(Math.random()*100)
    const tweetName = req.body.name
    const tweetDescription = req.body.description
    
    try{
       await db.raw(`INSERT INTO tweets (id,name,description) VALUES ('${id}','${tweetName}','${tweetDescription}');`)
       res.json({message:'Tweet created Successfully'})
    }catch(err){
        res.json(err)
    }
})

router.get('/tweets/:id',async(req,res)=>{
    console.log('These are the req.params:',req.params)
    const id = req.params.id

    try {
        const tweet = await db.raw
        (`
        SELECT * FROM tweets WHERE tweets.id = '${id}';
        `)
        //console.log(tweet)
        res.json(tweet.rows[0])
    } catch (err) {
        res.json(err)
        
    }
})

router.patch('/tweets/:id',async(req,res)=> {
    const id = req.params.id
    const {name,description} = req.body
    console.log('destructured name:',name)
    try {
        await db.raw
        (`
            UPDATE tweets SET name = '${name}',
            description = '${description}'
            WHERE id = '${id}';
        `)
        res.json({message:'Tweet updated successfully!'})
    } catch (err) {
        res.json({message:err})
        
    }
})

router.delete('/tweets/:id',async(req,res) => {
    const id = req.params.id

    try {
        await db.raw
        (`
           DELETE FROM tweets WHERE id = '${id}';
        `)
        res.json({message:'Tweet deleted successfully!'})
    } catch (err) {
        res.json({message:err})
        
    }
})

module.exports = router