import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { QuicksearchComponent } from './quicksearch/quicksearch.component';
import { AdvancedSearchComponent } from './advanced-search/advanced-search.component';
import { AuthorSearchComponent } from './author-search/author-search.component';
import { RouterModule, Routes } from '@angular/router';

const appRoutes: Routes = [
  {path:'quicksearch', component: QuicksearchComponent},
  {path:'advancedsearch', component: AdvancedSearchComponent},
  {path:'authorsearch', component: AuthorSearchComponent},
  {path:'', component: QuicksearchComponent}
]

@NgModule({
  declarations: [
    AppComponent,
    QuicksearchComponent,
    AdvancedSearchComponent,
    AuthorSearchComponent
  ],
  imports: [
    BrowserModule,
    RouterModule.forRoot(appRoutes)
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
