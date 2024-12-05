# AppTraqer - Core Features Overview

## 1. Purpose and Audience
- **Purpose**: AppTraqer is an application tracking system designed to help users manage job applications and track progress during the job search process.
- **Target Audience**: Unemployed individuals, including those applying via recruiters, headhunters, or job boards.

## 2. Core Features

### A. Job Tracking
- **Job Application Details**:
  - **Job Title**: The title of the job being applied for.
  - **Job Type**: Full-time, part-time, contract, etc.
  - **Job Description**: Description of the job duties.
  - **Job Location**: The job’s location.
  - **Job Status**: Tracks progress (e.g., Applied, Interview Scheduled, Offer).
  - **Salary Range Posted**: 
  - **Salary Range Requested**: 
- **Job Source**:
  - **Job Source Type**: Where the job was found (e.g., Company Website, Job Board, Recruiter, Referral).
  - **Referral Source**: The person or system from which the job was referred, if applicable.
  - **Company**: The company offering the job, linked to the job details.
- **Future Enhancements Recommendation**: To further improve the Job Tracking feature, a future enhancement could include automation of job data entry through integration with platforms like LinkedIn or Indeed.  
  - **Auto-Population**: Integrating with these platforms would allow job details such as title, description, and company to populate automatically after a user applies.  
  - **Job Import Feature**: Alternatively, an "Import Job" button could allow users to paste a job posting URL. The system would detect the platform and scrape relevant information to create a draft job application.  

These enhancements would reduce manual input and enhance user experience, b

### B. Recruiters and Agencies
- **Job and Recruiter Relationship**:
  - A job must be linked to either a company or a recruiter agency, or both. This establishes the source of the job application.

- **Recruiter Agency**:
  - **Agency Name**: The name of the recruiting agency.
  - **Agency Location**: The agency’s location.
  - **Recruiters**: Recruiters linked to the agency, with contact details.

- **Recruiter-to-Company Relationship**:
  - **Job Source**: Indicates if the recruiter is submitting candidates or simply sharing job details. Some jobs may not disclose the company upfront.

### C. Company Details
- **Company Information**:
  - **Company Name**: The name of the company offering the job.
  - **Industry**: Industry type (e.g., IT, Healthcare).
  - **Location**: Company address.
  - **Website**: Link to the company’s website.
  - **Company Type**: Startup, medium-sized, or large enterprise.
  - **Hiring Process**: General hiring process for the company.

- **Departments**:
  - **Department Name**: Tracks departments (e.g., Engineering, HR).
  - **Department Contacts**: People within each department involved in hiring.

- **Interviews and Interviewers**:
  - **Interviewer Name**: The person conducting the interview.
  - **Interviewer Title**: Job title of the interviewer (e.g., HR Manager).
  - **Interviewer Role**: Designates whether the person is the primary or secondary interviewer (e.g., Technical, HR).
  - **Interview Feedback**: Tracks feedback from the interviewer after each interview.
  - **Interview Notes**: Tracks nodes the applicatent had after each interview.

### D. Job Application Steps and Custom Workflow
- **Customizable Application Steps**:
  - Track multiple steps in the application process (e.g., Phone Interview, Technical Interview, On-site Interview, HR Interview).
  - **Step Names**: Can be user-configurable to reflect the specific company’s hiring process.
  - **Step Details**: Each step will have an associated description, scheduled date, and status (e.g., Pending, Completed).
  - **Step Outcome**: Track results from each step (e.g., Passed, Failed, Pending).

### E. Tracking Interviewer Relationships
- **Interviewer Relationship to Company**: The specific individuals conducting interviews for the company. This allows tracking of who is involved at different stages of the process.
  
- **Feedback and Notes**:
  - Each application step can have notes attached, providing feedback from the interviewer and helping track progress through the application pipeline.

### F. Relationships Between Entities
- **Job Details and Company**: A job can be associated with a company. Multiple jobs can exist under the same company, and each can be associated with specific departments.
- **Job Details and Recruiters**: Jobs can be connected to recruiters and recruiter agencies, helping track where the job was found and the relationship between recruiters and companies.
- **Company Departments and Interviewers**: Departments within the company will have interviewers linked to them, allowing tracking of who interviewed the candidate and from which department.

### G. Dashboard / Home Page

- **Purpose**: The home page serves to gamify and encourage consistent app usage by displaying key metrics and motivating users to set and achieve job application goals. It can also generate a printable report for the unemployment office.  
- **Key Metrics**:  
  - **Total Applications (Last 30 Days)**: Shows total applications submitted and the percentage of a user-defined target (default: 30 applications/month).  
  - **Initial Interviews (Last 30 Days)**: Tracks initial interviews and percentage of a target (default: 5/month).  
  - **Application-to-Interview Rate**: Displays the percentage of applications resulting in interviews, with a "health score" benchmark (e.g., 5% is typical).  
  - **LinkedIn Easy Apply Success Rate**: Tracks success rates specifically for LinkedIn Easy Apply submissions, with its own health score.  
  - **Second-Round Interviews**: Shows the number of second-round interviews (beyond HR screening) with a health score target (e.g., 50% of first-round interviews should lead to second rounds).  

- **Active Job Applications & Interviews**:  
  - **Columns**: Displays details like application date, company, job title, salary, and more.  
  - **Details View**: Expands to show job-specific information, interviews, and notes for each application.  

## 3. User Roles

- **Admin**: Admin users will have full access to all data, including managing user roles, configuring job types, recruiters, and company information, and overseeing the job application process.
  
- **User**: Users will have access to manage their job applications, including adding new applications, tracking their progress, and setting reminders for interview steps. Users can configure the steps in the job application process according to each company’s hiring procedures.

## 4. Tech Stack
- **Backend**: Java with Spring Framework (including ORM for database management).
- **Frontend**: React.js for building the user interface.
- **Database**: PostgreSQL, Microsoft SQL, or SQLite
- **API**: REST APIs for communication between the frontend and backend.
- **Version Control**: GitHub for code repository and open-source collaboration.
- **Issue Tracking and Project Management**: Jira for tracking issues, progress, and user stories. Free account for open-source projects.

## 5. Additional Features
- **User-configurable application steps**: Ability for users to define custom application process steps for each job.
- **Multiple recruiters and contacts**: Ability to track multiple recruiters per job and recruiter agency, including different office locations and contacts.
- **Interviewer feedback**: Ability to track feedback and status updates at each step of the hiring process.
- **Integration with job boards**: Ability to track and manage jobs found on various job boards and networking platforms.
