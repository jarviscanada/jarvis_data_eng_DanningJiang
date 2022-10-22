import { Component, OnInit } from '@angular/core';
import { TweetService } from '../tweet.service';
import { Router, ActivatedRoute} from '@angular/router';

@Component({
  selector: 'app-new-tweet-page',
  templateUrl: './new-tweet-page.component.html',
  styleUrls: ['./new-tweet-page.component.css']
})
export class NewTweetPageComponent implements OnInit {

  newTweet = {
    id:0,
    name:'',
    description:''
  }

  constructor(private _tweetService:TweetService, private _router:Router) { }

  ngOnInit(): void {
  }

  onChange(){
    console.log('changed!')
  }
  submitForm(){
    //update the component to send data to service
    console.log(this.newTweet)
    this._tweetService.createTweet(this.newTweet).subscribe(reponseData=>{
      console.log('This is the reponse data:',reponseData)
      this._router.navigate(['/'])
    })
  }

}
