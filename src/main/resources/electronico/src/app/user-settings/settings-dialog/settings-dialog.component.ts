import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {User} from "../../model/user.model";

@Component({
  selector: 'app-settings-dialog',
  templateUrl: './settings-dialog.component.html',
  styleUrls: ['./settings-dialog.component.css']
})
export class SettingsDialogComponent implements OnInit {

  constructor(public settings: MatDialogRef<SettingsDialogComponent>,
              @Inject(MAT_DIALOG_DATA) public user: User) { }

  onNoClick(): void {
    this.settings.close();
  }

  ngOnInit(): void {
  }

}
