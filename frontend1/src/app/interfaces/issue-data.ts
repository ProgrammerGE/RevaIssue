export interface IssueData {
  issueID: number;
  name: string;
  description: string;
  projectID: number;
  severity: number;
  priority: number;
  status: IssueStatus;
  comments: Comment[];
}

export interface Comment {
  author: string;
  text: string;
  dateCreated: string;
}

export type IssueStatus = 'OPEN' | 'IN_PROGRESS' | 'RESOLVED' | 'CLOSED';
