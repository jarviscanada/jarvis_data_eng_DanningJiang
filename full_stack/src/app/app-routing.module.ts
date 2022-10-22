import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomePageComponent } from './home-page/home-page.component';
import { NewTweetPageComponent } from './new-tweet-page/new-tweet-page.component';
import { DetailPageComponent } from './detail-page/detail-page.component';
import { EditPageComponent } from './edit-page/edit-page.component';
import { PageNotFoundComponent } from './page-not-found.component';

const routes: Routes = [
  //Add Components
  {path:'',component:HomePageComponent},
  {path:'home',component:HomePageComponent},
  {path:'new',component:NewTweetPageComponent},
  {path:'detail/:id',component:DetailPageComponent},
  {path:'edit/:id',component:EditPageComponent},
  {path:'**',component:PageNotFoundComponent}

]

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
