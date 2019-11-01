import LogRocket from "logrocket";
import { Injectable } from "@angular/core";

export class LogRocketServiceError extends Error {}

@Injectable()
export class LogRocketService {
  private isInitialized = false;
  private logRocket = LogRocket;

  public getSessionURL(): Promise<string> {
    return new Promise((resolve, reject) => {
      if (!this.isInitialized) {
        reject(new LogRocketServiceError("LogRocket is not initialized"));
      }

      this.logRocket.getSessionURL(sessionURL => {
        sessionURL
          ? resolve(sessionURL)
          : reject(new LogRocketServiceError("Failed to get session URL"));
      });
    });
  }

  public LogRocketInit() {
    this.logRocket.init("logrocket-project-endpoint");
    this.isInitialized = true;
  }
}
