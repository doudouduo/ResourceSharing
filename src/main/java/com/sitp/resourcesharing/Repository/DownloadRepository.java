package com.sitp.resourcesharing.Repository;

import com.sitp.resourcesharing.Entity.Download;
import com.sitp.resourcesharing.Entity.DownloadPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DownloadRepository extends JpaRepository<Download, DownloadPK> {
}
