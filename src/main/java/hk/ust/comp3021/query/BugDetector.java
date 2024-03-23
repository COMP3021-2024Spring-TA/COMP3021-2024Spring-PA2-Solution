package hk.ust.comp3021.query;

import hk.ust.comp3021.expr.*;
import hk.ust.comp3021.misc.*;
import hk.ust.comp3021.stmt.*;
import hk.ust.comp3021.utils.*;

import java.util.*;
import java.util.function.*;

public class BugDetector {
    ASTModule module = null;

    public BugDetector(ASTModule module) {
        this.module = module;
    }

    public Supplier<List<String>> detect = () -> {return null;};
}
