package com.example.RevaIssue.entity

@Data
@NoArgsConstructor
@Entity
@Table(name = "issues")
public class Issue {

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
@Column(name = "issue_id", nullable = false)
private Long issueID;

@Column(name = "issue_description")
private String description;

@Column(name = "project_id", nullable = false)
private Integer projectId;

@Column(name = "severity")
private Integer severity;

@Column(name = "priority")
private Integer priority;
}