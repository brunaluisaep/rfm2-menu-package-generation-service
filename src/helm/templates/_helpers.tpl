{{/* vim: set filetype=mustache: */}}
{{/*
Expand the name of the chart.
*/}}
{{- define "package-generation-service.name" -}}
{{- default .Chart.Name .Values.nameOverride | trunc 63 | trimSuffix "-" -}}
{{- end -}}

{{/*
Create a default fully qualified app name.
We truncate at 63 chars because some Kubernetes name fields are limited to this (by the DNS naming spec).
If release name contains chart name it will be used as a full name.
*/}}
{{- define "package-generation-service.fullname" -}}
{{- if .Values.fullnameOverride -}}
{{- .Values.fullnameOverride | trunc 63 | trimSuffix "-" -}}
{{- else -}}
{{- $name := default .Chart.Name .Values.nameOverride -}}
{{- if contains $name .Release.Name -}}
{{- .Release.Name | trunc 63 | trimSuffix "-" -}}
{{- else -}}
{{- printf "%s-%s" .Release.Name $name | trunc 63 | trimSuffix "-" -}}
{{- end -}}
{{- end -}}
{{- end -}}

{{/*
Create chart name and version as used by the chart label.
*/}}
{{- define "package-generation-service.chart" -}}
{{- printf "%s-%s" .Chart.Name .Chart.Version | replace "+" "_" | trunc 63 | trimSuffix "-" -}}
{{- end -}}

{{/*
Common labels
*/}}
{{- define "package-generation-service.labels" -}}
helm.sh/chart: {{ include "package-generation-service.chart" . }}
{{ include "package-generation-service.selectorLabels" . }}
{{- if .Chart.AppVersion }}
app.kubernetes.io/version: {{ .Chart.AppVersion | quote }}
{{- end }}
app.kubernetes.io/managed-by: {{ .Release.Service }}
{{- end }}

{{/*
Selector labels
*/}}
{{- define "package-generation-service.selectorLabels" -}}
app.kubernetes.io/name: {{ include "package-generation-service.name" . }}
app.kubernetes.io/instance: {{ .Release.Name }}
{{- end }}

{{/*
Define Market Label
*/}}
{{- define "package-generation-service.market" }}
{{- if .Values.packageGenerationService.market }}
{{- printf .Values.packageGenerationService.market }}
{{- end }}
{{- end }}

{{/*
Define Environment Label
*/}}
{{- define "package-generation-service.env" }}
{{- if .Values.packageGenerationService.env }}
{{- printf .Values.packageGenerationService.env }}
{{- end }}
{{- end }}

{{/*
New Relic Labels

{{- define "package-generation-service.newRelicLabels" -}}
appName: {{ include "package-generation-service.name" . }}
market: {{ include "package-generation-service.market" . }}
environment: {{ include "package-generation-service.env" . }}
{{- end }}
*/}}
