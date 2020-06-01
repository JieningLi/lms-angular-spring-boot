import { NgModule } from "@angular/core";
import { Routes, RouterModule } from "@angular/router";
import { HomeComponent } from "./home/home.component";
import { LayoutComponent } from "./layout/layout.component";
import { AdminComponent } from "./admin/admin.component";
import { BorrowerComponent } from "./borrower/borrower.component";
import { LibrarianComponent } from "./librarian/librarian.component";
import { AuthorComponent } from "./admin/author/author.component";
import { SearchBookComponent } from "./borrower/search-book/search-book.component";
import { CheckLoansComponent } from './borrower/check-loans/check-loans.component';
import { LibraryComponent } from './librarian/library/library.component';
import { UpdateLoanComponent} from './librarian/update-loan/update-loan.component';

export const routes: Routes = [
  {
    path: "",
    component: LayoutComponent,
    children: [
      {
        path: "",
        redirectTo: "lms/home",
        pathMatch: "full",
      },
      {
        path: "lms/home",
        component: HomeComponent,
      },
      {
        path: "lms/admin",
        component: AdminComponent,
      },
      {
        path: "lms/borrower",
        component: BorrowerComponent,
      },
      {
        path: "lms/borrower/search-book",
        component: SearchBookComponent,
      },
      {
        path: "lms/borrower/loan",
        component: CheckLoansComponent,
      },
      {
        path: "lms/librarian",
        component: LibrarianComponent,
      },
      {
        path: "lms/admin/author",
        component: AuthorComponent,
      },
      {
        path: 'lms/librarian/library',
        component: LibraryComponent
      },
      {
        path: 'lms/librarian/loan',
        component: UpdateLoanComponent,
      }
    ],
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
