export interface IssueData {
  issueID: number;
  name: string;
  description: string;
  projectID: number;
  severity: number;
  priority: number;
  status: IssueStatus;
  comments: Comment[];
  project?: Project
}

export interface Comment {
  author: string;
  text: string;
  dateCreated: string;
}

export interface Project {
      projectDescription: string,
      projectID: 1,
      projectName: string
}

export type IssueStatus = 'OPEN' | 'IN_PROGRESS' | 'RESOLVED' | 'CLOSED';
