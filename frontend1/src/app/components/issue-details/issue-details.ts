import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-issue-details',
  imports: [FormsModule],
  templateUrl: './issue-details.html',
  styleUrl: './issue-details.css',
})
export class IssueDetails {
  issueTitle: string = 'Test';
  issueDescription: string = '';

  userRole: 'tester' | 'developer' | 'admin' = 'tester';
  isEditing: boolean = false;

  // Button Actions
  onEdit() {
    this.isEditing = true;
  }
  onSubmit() {
    this.isEditing = false;
    alert('Issue updated!');
  }
  onClaim() {
    alert('Issue claimed by you!');
  }
  onResolve() {
    alert('Issue marked as resolved!');
  }
}
