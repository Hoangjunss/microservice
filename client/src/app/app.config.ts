import { ApplicationConfig, provideZoneChangeDetection } from '@angular/core';
import { provideRouter } from '@angular/router';

import { routes } from './app.routes';
import { provideClientHydration } from '@angular/platform-browser';
import { provideHttpClient,withFetch  } from '@angular/common/http';
import { DatePipe } from '@angular/common';

export const appConfig: ApplicationConfig = {
  providers: [DatePipe, provideZoneChangeDetection({ eventCoalescing: true }), provideRouter(routes), provideClientHydration(), provideHttpClient(withFetch())]
};