import { Component, OnInit } from '@angular/core';
import { TweetService } from '../tweet.service';
import { Router } from '@angular/router';

// Decorator/Annotations/Metadata
// Lets Angular know that this class object is an Angular component
@Component({
  selector: 'app-home-page',
  templateUrl: './home-page.component.html',
  styleUrls: ['./home-page.component.css']
})
export class HomePageComponent implements OnInit {

  tweets: any[] = []

  constructor(private _tweetService:TweetService,private _router:Router) { }

  ngOnInit() {
    this._tweetService.fetchAllTweets().subscribe( (responseData: any) => {
      this.tweets = responseData
      console.log('Response')
    })
  }

  deleteTweet(id:any){
    this._tweetService.deleteTweet(id).subscribe(data =>{
      console.log(data)
      //this._router.navigate(['/'])
      this.ngOnInit()
    })
  }

}
