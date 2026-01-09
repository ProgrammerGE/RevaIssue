import { CommentData } from './comment-data';

export interface IssueData {
  issueID: number;
  name: string;
  description: string;
  projectID: number;
  severity: number;
  priority: number;
  status: IssueStatus;
  comments: CommentData[];
}

export type IssueStatus = 'OPEN' | 'IN_PROGRESS' | 'RESOLVED' | 'CLOSED';
