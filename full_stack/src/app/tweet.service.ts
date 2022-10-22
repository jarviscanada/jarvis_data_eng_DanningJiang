import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http'

@Injectable({
  providedIn: 'root'
})
export class TweetService {

  private SERVER_URL:string ='http://localhost:4100/'

  constructor(private http:HttpClient) { }

  fetchAllTweets(){
    return this.http.get(this.SERVER_URL + 'tweets')

  }

  createTweet(newTweet:any){
    //return this.http.post(this.SERVER_URL + 'tweets',{...newTweet})
    return this.http.post(this.SERVER_URL + 'tweets', newTweet )
  }

  fetchTweetDetail(id: any){
    return this.http.get(this.SERVER_URL + `tweets/${id}`)
  }

  updateTweet(id:any,updatedTweet:any){
    return this.http.patch(this.SERVER_URL + `tweets/${id}`,updatedTweet)
  }

  deleteTweet(id:any){
    return this.http.delete(this.SERVER_URL + `tweets/${id}`)
  }
}
