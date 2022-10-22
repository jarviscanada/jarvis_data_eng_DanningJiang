import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { TweetService } from '../tweet.service';

@Component({
  selector: 'app-detail-page',
  templateUrl: './detail-page.component.html',
  styleUrls: ['./detail-page.component.css']
})
export class DetailPageComponent implements OnInit {

 
  tweetDetail:any;
  id:any; //available in our html template

  constructor(private route:ActivatedRoute, private _tweetService:TweetService) { }

  ngOnInit(): void {
    //Grabbed the route param from the route object
    this.route.params.subscribe(params => {
      console.log('params',params)
      //store the id of params in our own id property
      this.id = parseInt(params['id'])
    })

    //1.write the call to the service
    //2.define the service Get tweet detail function
    //3.difine the route/controller for the tweet detail on the express side
    this._tweetService.fetchTweetDetail(this.id).subscribe(data => {
      console.log('TweetDetail:',data)
      this.tweetDetail = data
    })


  }

}
