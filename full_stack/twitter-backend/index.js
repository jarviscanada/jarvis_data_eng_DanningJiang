
const express = require('express')
//const bodyParser = require('body-parser')
const tweetController = require('./controllers/tweets')
const cors = require('cors')
const app = express()

// app.use((req,res,next)=>{
//     //console.log('This is the req.body',req.body)
//     console.log(`${req.method} at path ${req.path}`)
//     next()
// })

//add parse data middleware
app.use(express.json())
//app.use(express.urlencoded({extended:false}))
//app.use(bodyParser.json())

app.use(cors())

//use router
app.use('/',tweetController)
//app.use(tweetController)



app.listen(4100,() => {
    console.log('app is listening on port 4100')
})