package keyhub.data.tbl.stream;

import keyhub.data.tbl.Tbl;

import java.util.stream.BaseStream;

public interface TblStream extends BaseStream<Tbl, TblStream> {
    Tbl toTbl();
}
