import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { CommentData } from '../interfaces/comment-data';
import { HttpClient } from '@angular/common/http';
import { JwtTokenStorage } from './jwt-token-storage';

@Injectable({
  providedIn: 'root',
})
export class CommentService {
  private commentsSubject = new BehaviorSubject<CommentData[]>([]);
  private baseUrl = 'http://localhost:8080';

  constructor(private http: HttpClient, private tokenStorage: JwtTokenStorage) {}

  getCommentsSubject() {
    return this.commentsSubject;
  }

  loadComments(issueId: number): void {
    this.http.get<CommentData[]>(`${this.baseUrl}/common/issues/${issueId}/comments`).subscribe({
      next: (comments) => this.commentsSubject.next(comments.reverse()),
      error: (err) => console.error('Failed to load comments', err),
    });
  }

  addComment(issueId: number, text: string): void {
    if (!text.trim()) return;

    const headers = {
      Authorization: `Bearer ${this.tokenStorage.getToken()}`,
    };

    this.http
      .post<CommentData>(`${this.baseUrl}/common/issues/${issueId}/comments`, { text }, { headers })
      .subscribe({
        next: (newComment) => {
          const current = this.commentsSubject.value;
          this.commentsSubject.next([newComment, ...current]);
        },
        error: (err) => console.error('Failed to add comment', err),
      });
  }

  getCommentSubject() {
    return this.commentsSubject;
  }
}
