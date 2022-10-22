import { Component, OnInit } from '@angular/core';
import { TweetService } from '../tweet.service';
import {Router,ActivatedRoute} from '@angular/router'

@Component({
  selector: 'app-edit-page',
  templateUrl: './edit-page.component.html',
  styleUrls: ['./edit-page.component.css']
})
export class EditPageComponent implements OnInit {

  //currentTweet:any
  updatedTweet:any = {
    id:0,
    name:'',
    description:''
  }
  id:number | undefined;

  constructor(private _tweetService:TweetService, private route:ActivatedRoute, private _router:Router) { }

  ngOnInit() {
    //grab the current tweet from the database
    this.route.params.subscribe(params => {
      this.id = parseInt(params['id'])
    })
    this._tweetService.fetchTweetDetail(this.id).subscribe(data=> {
      this.updatedTweet = data
    })

  }

  onChange(){
  }

  submitForm() { 

    console.log('Updated Tweet Data',this.updatedTweet)
    //send updated tweets data to the database
    this._tweetService.updateTweet(this.id,this.updatedTweet).subscribe(data => {
      console.log('This is the response:',data)
      this._router.navigate([`/detail/${this.id}`]) //redirect to the detail page after the update
      //this._router.navigate(['/']) // redirect to the home page
    })

  }



}
