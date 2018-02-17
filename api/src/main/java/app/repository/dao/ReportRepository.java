package app.repository.dao;

import app.repository.entity.Report;
import app.repository.etc.ReportSearchParams;

import java.util.List;

public interface ReportRepository extends CrudRepository<Report>, Searchable<Report, ReportSearchParams> {
}
